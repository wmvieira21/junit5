package suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import service.AccountServiceTest;

@Suite
@SuiteDisplayName("Test Suite")
@SelectPackages(value = { "service", "domains", "infra" })
public class SuiteTest {

}
