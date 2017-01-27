/**
 *    Copyright 2013-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.cdi.samples;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Sample filter, just for testing Mapper injection in a filter.
 * @author Frank D. Martinez M. [mnesarco], Jan 27, 2017
 */
@WebFilter(filterName = "SampleWebFilter", urlPatterns = {"/*"})
public class SampleWebFilter implements Filter {

  private static final Logger LOG = Logger.getLogger(SampleWebFilter.class.getName());
  
  
  @Inject
  UserMapper userMapper;
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
      User user = userMapper.getUser(1);
      LOG.log(Level.INFO, "Mapper called on filter doFilter, User = {0}", user.getName());
    
      chain.doFilter(request, response);
    
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    
      User user = userMapper.getUser(1);
      LOG.log(Level.INFO, "Mapper called on filter init, User = {0}", user.getName());
  
  }

  @Override
  public void destroy() {
  }
  
}
