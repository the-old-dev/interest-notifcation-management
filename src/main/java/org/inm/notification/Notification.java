package org.inm.notification;

import java.io.Serializable;
import java.util.List;

import org.dizitart.no2.objects.Id;
import org.inm.interest.Interest;
import org.inm.subscription.Subscription;

public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String subscriberEmail;
	private Subscription subscription;
	private List<Interest> interests;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubscriberEmail() {
		return subscriberEmail;
	}

	public void setSubscriberEmail(String subscriberEmail) {
		this.subscriberEmail = subscriberEmail;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public List<Interest> getInterests() {
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	void enshureId() {
		setId(createId());
	}

	static int createID(String aSubscriberEmail, Subscription aSubscription) {
	    
	    if (!isReadyForIdCreation(aSubscriberEmail, aSubscription)) {
	       throw new IllegalArgumentException("Parameters are null, empty or any fields of them"); 
	    }
	    
		String idBase = aSubscriberEmail + aSubscription.getName() + aSubscription.getWebsiteUrl().toString()
				+ aSubscription.getSubscribableUrl();
		for (String rule : aSubscription.getRules()) {
			idBase = idBase + rule;
		}
		return idBase.hashCode();
	}

    private int createId() {
		return createID(this.subscriberEmail, this.subscription);
	}
	
	private static boolean isReadyForIdCreation(String aSubscriberEmail, Subscription aSubscription) {
	   
	   if (isNullOrEmpty(aSubscriberEmail)) {
	        return false;
	   }
	    
	   if ( isNotFullyFilled(aSubscription) )  {
	        return false;
	   }
	    
	   return true;
	
	}
	
	private static boolean isNotFullyFilled(Subscription subscription) {
	    
	   if (subscription == null) {
	       return true;
	   }
	    
	   if (isNullOrEmpty(subscription.getName())) {
	       return true;
	   }

	   if (isNullOrEmpty(subscription.getWebsiteUrl())) {
	       return true;
	   }
	   
	   if (isNullOrEmpty(subscription.getSubscribableUrl().toExternalForm())) {
	       return true;
	   }
	   
	   for (String rule : subscription.getRules()) {
    	   if (isNullOrEmpty(rule)) {
    	       return true;
    	   }  
       }
	    
       return false;
	    
	}

    private static boolean isNullOrEmpty(String value) {
        
        if (value == null || value.equals("") ) {
            return true;
        } 
        
        return false;
        
    }
}
