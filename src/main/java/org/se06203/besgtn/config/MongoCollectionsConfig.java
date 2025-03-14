package org.se06203.besgtn.config;

import jakarta.annotation.PostConstruct;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.bson.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Component
public class MongoCollectionsConfig {

    private final MongoTemplate mongoTemplate;

    public MongoCollectionsConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void seedDatabase() {
        seedCategories();
        seedConversations();
        seedFeedBacks();
        seedMessages();
        seedOrganizations();
        seedPlans();
        seedScheduleException();
        seedSchedules();
        seedTaskManagement();
        seedTasks();
        seedUsers();
    }

    private void seedCategories() {
        if (mongoTemplate.getCollection("categories").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("name", "Work")
                    .append("color", "#FF5733"), "categories");
        }
    }

    private void seedConversations() {
        if (mongoTemplate.getCollection("conversations").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("userId", UUID.randomUUID().toString())
                    .append("startedAt", LocalDateTime.now().minusHours(2).toString())
                    .append("endedAt", LocalDateTime.now().toString())
                    .append("messageIds", List.of(UUID.randomUUID().toString())), "conversations");
        }
    }

    private void seedFeedBacks() {
        if (mongoTemplate.getCollection("feedbacks").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("userId", UUID.randomUUID().toString())
                    .append("adminId", UUID.randomUUID().toString())
                    .append("message", "Great service!"), "feedbacks");
        }
    }

    private void seedMessages() {
        if (mongoTemplate.getCollection("messages").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("conversationId", UUID.randomUUID().toString())
                    .append("message", new Document("text", "Hello, how are you?"))
                    .append("createdAt", LocalDateTime.now().toString()), "messages");
        }
    }

    private void seedOrganizations() {
        if (mongoTemplate.getCollection("organizations").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("name", "Tech Corp")
                    .append("adminId", UUID.randomUUID().toString())
                    .append("members", List.of(UUID.randomUUID().toString())), "organizations");
        }
    }

    private void seedPlans() {
        if (mongoTemplate.getCollection("plans").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("epicId", UUID.randomUUID().toString())
                    .append("date", LocalDate.now().toString())
                    .append("quantityTask", 5)
                    .append("taskIds", List.of(UUID.randomUUID().toString())), "plans");
        }
    }

    private void seedScheduleException() {
        if (mongoTemplate.getCollection("scheduleException").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("scheduleId", UUID.randomUUID().toString())
                    .append("exceptionDate", LocalDate.now().plusDays(1).toString())
                    .append("reason", "Holiday")
                    .append("createdAt", LocalDateTime.now().toString()), "scheduleException");
        }
    }

    private void seedSchedules() {
        if (mongoTemplate.getCollection("schedules").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("userId", UUID.randomUUID().toString())
                    .append("name", "Morning Workout")
                    .append("description", "Exercise for 30 minutes")
                    .append("startTime", LocalTime.of(6, 0).toString())
                    .append("endTime", LocalTime.of(6, 30).toString())
                    .append("date", LocalDate.now().toString())
                    .append("repeat", "Daily")
                    .append("repeatEndDate", LocalDate.now().plusMonths(1).toString())
                    .append("remindMe", true)
                    .append("categoryId", UUID.randomUUID().toString()), "schedules");
        }
    }

    private void seedTaskManagement() {
        if (mongoTemplate.getCollection("taskManagement").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("nameEpic", "Project A")
                    .append("type", "Development")
                    .append("description", "Build a new feature")
                    .append("taskIds", List.of(UUID.randomUUID().toString())), "taskManagement");
        }
    }

    private void seedTasks() {
        if (mongoTemplate.getCollection("tasks").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("epicId", UUID.randomUUID().toString())
                    .append("description", "Implement authentication"), "tasks");
        }
    }

    private void seedUsers() {
        if (mongoTemplate.getCollection("users").countDocuments() == 0) {
            mongoTemplate.insert(new Document()
                    .append("_id", UUID.randomUUID().toString())
                    .append("userName", "john_doe")
                    .append("firstName", "John")
                    .append("lastName", "Doe")
                    .append("email", "john@example.com")
                    .append("password", "hashed_password")
                    .append("phone", "123456789")
                    .append("gender", "Male")
                    .append("hobbies", "Reading, Coding")
                    .append("Occupation", "Software Engineer")
                    .append("roles", List.of("USER", "ADMIN")), "users");
        }
    }
}
