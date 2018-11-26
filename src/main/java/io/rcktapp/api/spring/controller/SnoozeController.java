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

import javax.servlet.ServletContext;
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

import io.rcktapp.api.service.Snooze;

/**
 * @author kfrankic
 *
 */
@RestController
public class SnoozeController implements InitializingBean
{
   Logger         log    = LoggerFactory.getLogger(SnoozeController.class);

   @Autowired
   ResourceLoader resourceLoader;

   @Autowired
   Environment    environment;

   Snooze         snooze = null;

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

         ResourceLoaderServletContext ctx = new ResourceLoaderServletContext(resourceLoader);

         snooze = new Snooze()
            {
               @Override
               public ServletContext getServletContext()
               {
                  return ctx;
               }
            };

         snooze.setProfile(profile);
         snooze.init();

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
      snooze.service(req, resp);
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
         }
         return path;
      }

   }

}
