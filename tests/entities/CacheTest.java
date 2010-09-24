package entities;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.loquatic.cerescan.api.entities.ScannedForm;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManager;
import com.loquatic.cerescan.api.persistence.managers.SessionInfoManagerFactory;

public class CacheTest {
	private static Log log = LogFactory.getLog(CacheTest.class);

	private long sessionId = 20 ;
	
	@Test
	public void testDuplicateRef() {
		SessionInfoManager sessionManager = SessionInfoManagerFactory
				.getInstance().getSession(sessionId);
		List<ScannedForm> forms = new ArrayList<ScannedForm>();
		if (sessionManager.getSessionInfo().getScannedForms() != null) {
			System.out.println("not null");
			forms = sessionManager.getSessionInfo().getScannedForms();
			System.out.println(forms.size());
		}
		forms.add(new ScannedForm());
		System.out.println("After insert: " + forms.size());
	}

	@Test
	public void testReRunDuplicateRef() {
		// completely new lookup
		SessionInfoManager sessionManager = SessionInfoManagerFactory
				.getInstance().getSession(20);

		List<ScannedForm> forms = new ArrayList<ScannedForm>();
		if (sessionManager.getSessionInfo().getScannedForms() != null) {
			System.out.println("not null");
			forms = sessionManager.getSessionInfo().getScannedForms();
			System.out.println(forms.size());
		}
		forms.add(new ScannedForm());
		System.out.println("Should be value of 1 : " + forms.size());
		Assert.assertEquals(1, forms.size());
	}
}