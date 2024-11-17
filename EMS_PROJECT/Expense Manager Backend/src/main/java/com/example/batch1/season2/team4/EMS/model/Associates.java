package com.example.batch1.season2.team4.EMS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="season2_batch1_team4_EMS_associates")
public class Associates {

@Override
	public String toString() {
		return "Associates [associateId=" + associateId + ", associateName=" + associateName + ", position=" + position
				+ ", reportingManagerId=" + reportingManagerId + ", emailId=" + emailId + ", amount=" + amount
				+ ", notificationCount=" + notificationCount + "]";
	}

@Id
@Column(name = "associate_id")
String associateId;

@Column(name = "associate_name")
String associateName;

@Column(name = "position")
String position;

@Column(name = "reporting_manager_id")
String reportingManagerId;

@Column(name = "email_id")
String emailId;

@Column(name = "budget")
float amount;

@Column(name = "notification_count")
int notificationCount;

public Associates() {

}

public Associates(String associateId, String associateName, String position, String reportingManagerId,
String emailId, float amount, int notificationCount) {
super();
this.associateId = associateId;
this.associateName = associateName;
this.position = position;
this.reportingManagerId = reportingManagerId;
this.emailId = emailId;
this.amount = amount;
this.notificationCount = notificationCount;
}

public String getAssociateId() {
return associateId;
}

public void setAssociateId(String associateId) {
this.associateId = associateId;
}

public String getAssociateName() {
return associateName;
}

public void setAssociateName(String associateName) {
this.associateName = associateName;
}

public String getPosition() {
return position;
}

public void setPosition(String position) {
this.position = position;
}

public String getReportingManagerId() {
return reportingManagerId;
}

public void setReportingManagerId(String reportingManagerId) {
this.reportingManagerId = reportingManagerId;
}

public String getEmailId() {
return emailId;
}

public void setEmailId(String emailId) {
this.emailId = emailId;
}

public float getAmount() {
return amount;
}

public void setAmount(float amount) {
this.amount = amount;
}

public int getNotificationCount() {
return notificationCount;
}

public void setNotificationCount(int notificationCount) {
this.notificationCount = notificationCount;
}

}
