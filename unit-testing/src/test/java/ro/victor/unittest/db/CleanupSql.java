// SOLUTION
package ro.victor.unittest.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts="/cleanup.sql")
public @interface CleanupSql {

}
