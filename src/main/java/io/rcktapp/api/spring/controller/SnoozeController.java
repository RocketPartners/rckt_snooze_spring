/*
 * Copyright (c) 2015-2018 Rocket Partners, LLC
 * http://rocketpartners.io
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 * //
 */
package io.rcktapp.api.spring.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.rcktapp.api.service.Servlet;

/**
 * @author kfrankic
 *
 */
@RestController
public class SnoozeController implements InitializingBean
{
   Logger         log     = LoggerFactory.getLogger(SnoozeController.class);

   @Autowired
   ResourceLoader resourceLoader;

   @Autowired
   Environment    environment;

   Servlet        servlet = new Servlet();

   @Override
   public void afterPropertiesSet() throws Exception
   {
      try
      {
         String[] activeProfiles = environment.getActiveProfiles();
         String profile = null;
         if (activeProfiles.length > 0)
         {
            profile = activeProfiles[0];
            log.info("Using profile '" + profile + "'");
         }
         else
         {
            log.info("No active spring profile was configured - use 'spring.profiles.active' to configure one");
         }

         final ResourceLoaderServletContext ctx = new ResourceLoaderServletContext(resourceLoader);

         servlet.getService().setResourceLoader(new io.rcktapp.api.service.Service.ResourceLoader()
            {
               @Override
               public InputStream getResource(String name)
               {
                  return ctx.getResourceAsStream(name);
               }
            });

         servlet.getService().setProfile(profile);
         servlet.getService().init();

      }
      catch (Exception e)
      {
         log.error("Error initializing snooze", e);
         throw e;
      }

   }

   @RequestMapping(value = "/**")
   public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception
   {
      servlet.service(req, resp);
   }

   static class ResourceLoaderServletContext extends MockServletContext
   {

      public ResourceLoaderServletContext(ResourceLoader resourceLoader)
      {
         super(resourceLoader);
      }

      @Override
      protected String getResourceLocation(String path)
      {
         if (path != null)
         {
            path = path.replace("/WEB-INF/", "classpath:");
            path = path.replace("WEB-INF/", "classpath:");
            
            if (!path.startsWith("classpath:"))
            {
               path = "classpath:" + path;
            }
         }
         return path;
      }

   }

}
