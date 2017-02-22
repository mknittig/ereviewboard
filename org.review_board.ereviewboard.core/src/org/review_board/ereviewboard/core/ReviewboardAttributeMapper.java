package org.review_board.ereviewboard.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;

/**
 * @author Robert Munteanu
 */
public class ReviewboardAttributeMapper extends TaskAttributeMapper {

    public enum Attribute {

        ID("id", "Id", TaskAttribute.TYPE_INTEGER, null),
        REPOSITORY("repository.title", "Repository", TaskAttribute.TYPE_SHORT_RICH_TEXT, TaskAttribute.KIND_DEFAULT),
        SUBMITTER("submitter.title", "Submitter", TaskAttribute.TYPE_PERSON, null),
        SUMMARY("summary", "Summary", TaskAttribute.TYPE_SHORT_RICH_TEXT, null),
        DESCRIPTION("description", "Description", TaskAttribute.TYPE_LONG_RICH_TEXT, null),
        TESTING_DONE("testing_done", "Testing done", TaskAttribute.TYPE_LONG_RICH_TEXT, null),
        STATUS("status", "Status", TaskAttribute.TYPE_SHORT_RICH_TEXT, TaskAttribute.KIND_DEFAULT),
        SHIP_IT("ship-it", "Ship-it", TaskAttribute.TYPE_SHORT_RICH_TEXT, TaskAttribute.KIND_DEFAULT),
        BUGS_CLOSED("bugs_closed", "Bugs closed", TaskAttribute.TYPE_SHORT_RICH_TEXT, TaskAttribute.KIND_DEFAULT),
        PUBLIC("public", "Public", TaskAttribute.TYPE_BOOLEAN, TaskAttribute.KIND_DEFAULT),
        BRANCH("branch", "Branch", TaskAttribute.TYPE_SHORT_RICH_TEXT, TaskAttribute.KIND_DEFAULT),
        CHANGENUM("changenum", "Change number", TaskAttribute.TYPE_SHORT_RICH_TEXT, TaskAttribute.KIND_DEFAULT),
        TARGET_PEOPLE("target_people", "People", TaskAttribute.TYPE_PERSON, TaskAttribute.KIND_PEOPLE),
        TARGET_GROUPS("target_groups", "Groups", TaskAttribute.TYPE_PERSON, TaskAttribute.KIND_PEOPLE),
        LAST_UPDATED("last_updated", "Last updated", TaskAttribute.TYPE_DATETIME, null),
        TIME_ADDED("time_added", "Time added", TaskAttribute.TYPE_DATETIME, null);

        private final String jsonAttributeName;
        private final String displayName;
        private final String attributeType;
        private final String attributeKind;

        private Attribute(String jsonAttributeName, String displayName, String attributeType, String attributeKind) {

            this.jsonAttributeName = jsonAttributeName;
            this.displayName = displayName;
            this.attributeType = attributeType;
            this.attributeKind = attributeKind;
        }

        public String getJsonAttributeName() {

            return jsonAttributeName;
        }

        public String getDisplayName() {

            return displayName;
        }

        public String getAttributeType() {

            return attributeType;
        }

        public String getAttributeKind() {
         
            return attributeKind;
        }

    }

    private Map<String, ReviewboardAttributeMapper.Attribute> taskAttributeToMantisAttributes = new HashMap<String, ReviewboardAttributeMapper.Attribute>();
    {
        taskAttributeToMantisAttributes.put(TaskAttribute.PRODUCT, Attribute.REPOSITORY);
        taskAttributeToMantisAttributes.put(TaskAttribute.DESCRIPTION, Attribute.DESCRIPTION);
        taskAttributeToMantisAttributes.put(TaskAttribute.DATE_MODIFICATION, Attribute.LAST_UPDATED);
        taskAttributeToMantisAttributes.put(TaskAttribute.DATE_CREATION, Attribute.TIME_ADDED);
        taskAttributeToMantisAttributes.put(TaskAttribute.SUMMARY, Attribute.SUMMARY);
        taskAttributeToMantisAttributes.put(TaskAttribute.STATUS, Attribute.STATUS);
        taskAttributeToMantisAttributes.put(TaskAttribute.USER_REPORTER, Attribute.SUBMITTER);
    }

    public static DateFormat newDateFormat() {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    public static DateFormat newIso86011DateFormat() {
        
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
    }
    
    public static Date parseDateValue(String value) {
        
        if (value == null)
            return null;

        try {
            return newDateFormat().parse(value);
        } catch (ParseException e) {
            try {
                return new Date(Long.valueOf(value));
            } catch ( NumberFormatException nfe) {
                // ignore, pass-through
            }
            return null;
        }
    }

    public ReviewboardAttributeMapper(TaskRepository taskRepository) {
        super(taskRepository);
    }

    @Override
    public String mapToRepositoryKey(TaskAttribute parent, String key) {

        Attribute mapped = taskAttributeToMantisAttributes.get(key);
        if (mapped != null)
            return mapped.toString();

        return super.mapToRepositoryKey(parent, key).toString();
    }

    @Override
    public Date getDateValue(TaskAttribute attribute) {

        return parseDateValue(attribute.getValue());
    }
}