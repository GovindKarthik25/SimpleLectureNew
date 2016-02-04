package com.simplelecture.main.database;

import android.provider.BaseColumns;

/**
 * DataBase Columns
 * 
 * @author udaykumar.bh
 * 
 */

public abstract class DBColumns implements BaseColumns {

	// AlertMessageDbHelper 
	public static final String alertmessageid = "AlertMessageID";
	public static final String alertname = "AlertName";
	public static final String desc = "Desc";
	public static final String pageid = "PageID";
	public static final String pagename= "PageName";
	public static final String alerttype = "AlertType";
	public static final String buttontype1 = "ButtonType1";
	public static final String buttontype2 = "ButtonType2";
	public static final String audioreadouttext = "AudioReadOutText";

	// AudioNameDbHelper
	public static final String audiofileid = "AudioFileID";
	public static final String description = "Description";
	public static final String displaysequence = "DisplaySequence";
	public static final String referencetype = "ReferenceType";
	public static final String isactive = "IsActive";
	public static final String filename = "FileName";
	public static final String filepath = "FilePath";

	//ContentAudioFlieDBHelper 
	public static final String lessonid = "LessonID";
	public static final String questionid = "QuestionID";
	public static final String questionoptionid = "QuestionOptionID";


	//ContentDBHelper
	public static final String contentid = "ContentID";
	public static final String count = "count";
	public static final String audiofile = "audioFile";
	public static final String pageheader = "pageHeader";
	public static final String topheader = "topHeader";
	public static final String menu = "menu";
	public static final String contntname = "contentName";
	public static final String screentype = "screenType";
	public static final String lessons = "lessons";
	public static final String imageurl = "imageurl";
	public static final String publishers = "publishers";
	public static final String voicecommands = "voiceCommands";
	public static final String lessonattributes = "lessonAttributes";

	// ContentOptionDBHelper
	public static final String pagesectionid = "PageSectionID";
	public static final String name = "Name";
	public static final String displaytext = "DisplayText";
	public static final String status = "Status";
	public static final String sessions = "Sessions";


	// CoursesDBHelper
	public static final String courseid = "CourseID";
	public static final String createdby = "CreatedBy";
	public static final String updatedby = "UpdatedBy";	  
	public static final String coursename = "CourseName";
	public static final String createddate = "CreatedDate";
	public static final String updatedate = "UpdatedDate";
	public static final String specialityid = "SpecialityID";

	// CustomerDBHelper
	public static final String id = "id";
	public static final String username = "UserName";
	public static final String password = "Password";

	//DashBoardDBHelper
	public static final String userid = "UserID";
	public static final String topbanner = "TopBanner";
	public static final String content = "content";
	public static final String contentoptionname = "contentOptionName";

	//ErrorMessageDBHelper
	public static final String statuscodeid = "statuscodeid";
	public static final String questionpagestatuscode34 = "questionPageStatusCode34";
	public static final String code = "code";

	//HistoryDBHelper
	public static final String resultdescription = "ResultDescription";
	public static final String attributeid = "AttributeID";

	//LessonAttributeDBHelper
	public static final String attrid = "AttributeID";
	public static final String attrname = "AttributeName";

	//LessonDBHelper 
	public static final String lessonname = "LessonName";
	public static final String enddate = "EndDate";
	public static final String publisherid= "PublisherID";
	//public static final String specialtyid = "SpecialtyID";
	public static final String skippedquestionscount = "SkippedQuestionsCount";
	public static final String sectionslist = "SectionsList";
	public static final String lessontime= "LessonTime";
	public static final String lessontimeleft = "LessonTimeLeft";
	public static final String lessondeliveryid = "LessonDeliveryID";
	public static final String historylist = "HistoryList";
	public static final String sessionresultid = "SessionResultID";
	public static final String instructions = "Instructions";
	public static final String passingpercentage = "PassingPercentage";
	public static final String certificatetemplate = "CertificateTemplate";
	public static final String questionscount = "QuestionsCount";
	public static final String attributename = "AttributeName";
	public static final String contentname = "ContentName";

	//LoginMessagesDBHelper 

	// MenuDBHelper

	// OfflineDbHelper

	//OfflineLessonBdHelper
	public static final String offlinelessons = "offlineLessonsTimeLeft";

	//PageHeadingDbHelper

	// PublisherDbHelper
	public static final String publishername= "PublisherName";

	// QuestionAnswerDdHelper
	public static final String attemptedanswer = "attemptedAnswer";

	//QuestionOptionDbHelper
	public static final String optiontype = "OptionType";
	public static final String audiofilename = "AudioFileName";
	public static final String readouttext = "ReadoutText";
	public static final String extrastring = "ExtraString";
	public static final String displaysequenc = "DisplaySequenc";

	// QuestionPageStatusCodeDbHelper

	// QuestionsDbHelper
	public static final String questionname = "QuestionName";
	public static final String questionreadouttext = "QuestionReadOutText"; 
	public static final String availablepoints = "AvailablePoints";
	public static final String providedtime = "ProvidedTime";
	public static final String maxwaittime = "MaxWaitTime";
	public static final String minwaittime = "MinWaitTime";
	public static final String ismandatorytoanswer = "IsMandatoryToAnswer";
	public static final String userresponseid = "UserResponseID";

	//SettingsDBHelper
	public static final String voice = "voice";
	public static final String login = "login";

	//SpecialitiesDBHelper
	public static final String specialityname = "SpecialityName";
	public static final String courses = "Courses";

	//TopBannerDBHelper

	//TopHeadersDbHelper

	//UserAppDeatilsDBHelper
	public static final String synctime = "SyncTime";
	public static final String isproduction = "IsProduction";
	public static final String apiversion = "APIVersion";
	public static final String appversion = "AppVersion";

	// UserDbHelper
	public static final String firstname = "FirstName";
	public static final String lastname = "LastName";
	public static final String emailid = "EmailID";
	public static final String userstatus = "UserStatus";
	public static final String apikey = "ApiKey";

	//VoiceCommandsDbHelper
	public static final String voicecommandid = "VoiceCommandID";	
	public static final String commandname = "CommandName";

}
