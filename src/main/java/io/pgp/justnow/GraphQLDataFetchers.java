package io.pgp.justnow;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import io.pgp.justnow.entity.Task;
import io.pgp.justnow.repository.ListRepository;
import io.pgp.justnow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {
    @Autowired
    private TaskRepository taskRepository;

    private static List<Map<String, String>> tasks = Arrays.asList(
            ImmutableMap.of(
                    "id", "1",
                    "title", "Zrobic Magika",
                    "isDone", "false",
                    "priority", "5",
                    "list", "1"),
            ImmutableMap.of(
                    "id", "2",
                    "title", "Projekt z PO",
                    "isDone", "true",
                    "priority", "10",
                    "list", "1"),
            ImmutableMap.of(
                    "id", "3",
                    "title", "Trening",
                    "isDone", "false",
                    "description", "jd",
                    "priority", "99")
    );

    private static List<Map<String, String>> lists = Arrays.asList(
            ImmutableMap.of(
                    "id", "1",
                    "name", "Szkoła",
                    "author", "Inver")
    );

    public DataFetcher getAllTasks() {
        return _env -> tasks;
    }

    public DataFetcher getFindTask() {
        return env -> {
            String id = env.getArgument("id");
            return tasks
                    .stream()
                    .filter(task -> task.get("id").equals(id))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getTaskAggregateCount() {
        return _env -> tasks.size();
    }

    public DataFetcher getTaskList() {
        return env -> {
            Map<String, String> task = env.getSource();
            String listId = task.get("list");
            return lists
                    .stream()
                    .filter(list -> list.get("id").equals(listId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAllLists() {
        return _env -> lists;
    }

    public DataFetcher getFindList() {
        return env -> {
            String id = env.getArgument("id");
            return lists
                    .stream()
                    .filter(list -> list.get("id").equals(id))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getListAggregateCount() {
        return _env -> lists.size();
    }

    public DataFetcher getAddTask() {
        return env -> {
            Task task = new Task();
            task.setTitle(env.getArgument("title"));
            task.setDescription(env.getArgument("description"));
//            task.setPriority(env.getArgument("priority"));
            taskRepository.save(task);

            return task;
        };
    }

    public DataFetcher getModifyTask() {
        return env -> null;
    }

    public DataFetcher getDeleteTask() {
        return env -> null;
    }
}
