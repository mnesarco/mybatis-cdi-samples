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

import java.io.Reader;
import java.sql.Connection;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

/**
 * Required producer for the SqlSessionFactory
 * @author Frank D. Martinez M. [mnesarco], Jan 27, 2017
 */
@ApplicationScoped
public class Producers {

  private static final Logger LOG = Logger.getLogger(Producers.class.getName());

  /**
   * Simple SqlSessionFactory provider. Required by MyBatis.
   */
  @ApplicationScoped
  @Produces
  @SessionFactoryProvider
  public SqlSessionFactory produce() throws Exception {
    Reader reader = Resources.getResourceAsReader("mybatis-config_1.xml");
    SqlSessionFactory manager = new SqlSessionFactoryBuilder().build(reader);
    reader.close();
    // Insert some sample data...
    insertSampleData(manager);
    return manager;
  }

  /**
   * Sample data.
   */
  private void insertSampleData(SqlSessionFactory manager) throws Exception {
    try (SqlSession session = manager.openSession()) {
      Connection conn = session.getConnection();
      Reader reader = Resources.getResourceAsReader("CreateDB_1.sql");
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setLogWriter(null);
      runner.runScript(reader);
      reader.close();
    }
    catch (Exception ex) {
      LOG.info("Error executing SQL Script.... maybe DB already exists. This is just a test :D");
    }
  }

}
