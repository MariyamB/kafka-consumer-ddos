package kafka_consumer_ddos.kafka_consumer_ddos;

import junit.framework.Assert;

import org.junit.Test;

public class DdosDetectionUnitTest {
	
	DdosDetection ddosDetection= new DdosDetection();
	
	@Test
	public void testDdosDetectionSuccess()
	{
		String record="(129.192.143.166,1)";
		
		Assert.assertTrue(ddosDetection.findDdosDetection(record));
	}
	
	@Test
	public void testDdosDetectionFailure()
	{
		String badRecord="yjhdas,87123:12387";
		
		Assert.assertFalse(ddosDetection.findDdosDetection(badRecord));
	}

	@Test
	public void testvalidateIpwithMasterListSuccess()
	{
		String ip="129.192.143.166";
		Assert.assertTrue(ddosDetection.validateIpwithMasterList(ip));
	}
	
	@Test
	public void testvalidateIpwithMasterListFailure()
	{
		String ip="127.0.0.jhasd.1231.";
		Assert.assertFalse(ddosDetection.validateIpwithMasterList(ip));
	}
}
