/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mybatis.cdi.samples;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Frank D. Martinez M. <mnesarco at gmail.com>, Feb 22, 2017
 */
@Named("conv")
@ConversationScoped
public class SampleConversationScopedBean implements Serializable {

  @Inject
  UserMapper userMapper;
  
  public User getUser() {
    return userMapper.getUser(1);
  }
  
}
