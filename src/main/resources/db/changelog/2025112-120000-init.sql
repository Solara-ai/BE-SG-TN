
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
                       id INT PRIMARY KEY,
                       user_name VARCHAR(100) NOT NULL,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(10),
                       gender VARCHAR(10),
                       CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE user_role (
                           id INT PRIMARY KEY,
                           user_id INT NOT NULL,
                           role VARCHAR(10) NOT NULL,
                           CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES Users(id),
                           CONSTRAINT uk_user_role UNIQUE (user_id)
);

CREATE TABLE organization (
                              id INT PRIMARY KEY,
                              admin_id INT NOT NULL,
                              name VARCHAR(255) NOT NULL,
                              CONSTRAINT fk_organization_admin FOREIGN KEY (admin_id) REFERENCES Users(id),
                              CONSTRAINT uk_organization_admin UNIQUE (admin_id)
);

CREATE TABLE user_organization_xref (
                                        id INT PRIMARY KEY,
                                        organization_id INT NOT NULL,
                                        user_id INT NOT NULL,
                                        CONSTRAINT fk_user_org_organization FOREIGN KEY (organization_id) REFERENCES organization(id),
                                        CONSTRAINT fk_user_org_user FOREIGN KEY (user_id) REFERENCES Users(id),
                                        CONSTRAINT uk_user_organization UNIQUE (organization_id, user_id)
);

CREATE TABLE categories (
                            id INT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            color VARCHAR(100)
);

CREATE TABLE schedules (
                           id INT PRIMARY KEY,
                           user_id INT NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           description VARCHAR(255),
                           start_time TIME NOT NULL,
                           end_time TIME NOT NULL,
                           date DATE NOT NULL,
                           is_exception BOOLEAN DEFAULT FALSE,
                           repeat VARCHAR(20),
                           repeat_end_date TIMESTAMP,
                           remind_me BOOLEAN DEFAULT FALSE,
                           parent_schedule_id INT,
                           category_id INT,
                           CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES Users(id),
                           CONSTRAINT fk_schedule_parent FOREIGN KEY (parent_schedule_id) REFERENCES schedules(id),
                           CONSTRAINT fk_schedule_category FOREIGN KEY (category_id) REFERENCES categories(id),
                           CONSTRAINT uk_schedule_category_user UNIQUE (category_id, user_id)
);

CREATE TABLE schedule_exception (
                                    id INT PRIMARY KEY,
                                    schedule_id INT NOT NULL,
                                    exception_date DATE NOT NULL,
                                    reason TEXT,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    CONSTRAINT fk_schedule_exception_schedule FOREIGN KEY (schedule_id) REFERENCES schedules(id)
);

CREATE TABLE schedules_user_xref (
                                     id INT PRIMARY KEY,
                                     owner_id INT NOT NULL,
                                     schedule_id INT NOT NULL,
                                     user_id INT NOT NULL,
                                     date DATE NOT NULL,
                                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     deleted_at TIMESTAMP,
                                     CONSTRAINT fk_schedule_user_xref_owner FOREIGN KEY (owner_id) REFERENCES Users(id),
                                     CONSTRAINT fk_schedule_user_xref_schedule FOREIGN KEY (schedule_id) REFERENCES schedules(id),
                                     CONSTRAINT fk_schedule_user_xref_user FOREIGN KEY (user_id) REFERENCES Users(id),
                                     CONSTRAINT uk_schedule_user_xref UNIQUE (owner_id, schedule_id, user_id)
);

CREATE TABLE task_management (
                                 id INT PRIMARY KEY,
                                 name_epic VARCHAR(255) NOT NULL,
                                 type VARCHAR(20) NOT NULL,
                                 description VARCHAR(255)
);

CREATE TABLE tasks (
                       id INT PRIMARY KEY,
                       epic_id INT NOT NULL,
                       description VARCHAR(255) NOT NULL,
                       CONSTRAINT fk_task_epic FOREIGN KEY (epic_id) REFERENCES task_management(id),
                       CONSTRAINT uk_task_epic UNIQUE (epic_id)
);

CREATE TABLE plans (
                       id INT PRIMARY KEY,
                       epic_id INT NOT NULL,
                       date DATE NOT NULL,
                       quantity_task INT NOT NULL,
                       CONSTRAINT fk_plan_epic FOREIGN KEY (epic_id) REFERENCES task_management(id),
                       CONSTRAINT uk_plan_epic UNIQUE (epic_id)
);

CREATE TABLE plan_task_xref (
                                id INT PRIMARY KEY,
                                plan_id INT NOT NULL,
                                task_id INT NOT NULL,
                                CONSTRAINT fk_plan_task_plan FOREIGN KEY (plan_id) REFERENCES plans(id),
                                CONSTRAINT fk_plan_task_task FOREIGN KEY (task_id) REFERENCES tasks(id),
                                CONSTRAINT uk_plan_task UNIQUE (plan_id, task_id)
);

-- changeset author:system:13
CREATE TABLE feed_back (
                           id INT PRIMARY KEY,
                           user_id INT NOT NULL,
                           admin_id INT NOT NULL,
                           messages VARCHAR(255) NOT NULL,
                           CONSTRAINT fk_feedback_user FOREIGN KEY (user_id) REFERENCES Users(id),
                           CONSTRAINT fk_feedback_admin FOREIGN KEY (admin_id) REFERENCES Users(id),
                           CONSTRAINT uk_feedback_user_admin UNIQUE (user_id, admin_id)
);

CREATE TABLE conversations (
                               id INT PRIMARY KEY,
                               user_id INT NOT NULL,
                               started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               ended_at TIMESTAMP,
                               CONSTRAINT fk_conversation_user FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE messages (
                          id INT PRIMARY KEY,
                          conversation_id INT NOT NULL,
                          message JSONB NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_message_conversation FOREIGN KEY (conversation_id) REFERENCES conversations(id)
);