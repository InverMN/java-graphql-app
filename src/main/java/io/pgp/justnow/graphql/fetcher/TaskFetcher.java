package io.pgp.justnow.graphql.fetcher;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import io.pgp.justnow.entity.List;
import io.pgp.justnow.entity.Task;
import io.pgp.justnow.repository.ListRepository;
import io.pgp.justnow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaskFetcher {
    @Autowired
    private TaskRepository tasks;

    @Autowired
    private ListRepository lists;

    public DataFetcher getAll() {
        return _env -> tasks.findAll();
    }

    public DataFetcher getOne() {
        return env -> tasks.findById( Integer.parseInt(env.getArgument("id")));
    }

    public DataFetcher getTaskList() {
        return env -> {
            Task task = env.getSource();
            List list = task.getList();

            return list == null ? null : lists.findById(list.getId());
        };
    }

    public DataFetcher getCount() {
        return env -> {
          java.util.List<Task> tasks = env.getSource();
          return tasks.size();
        };
    }

    public DataFetcher addOne() {
        return env -> {
            Task task = new Task();
            Map<String, Object> input = env.getArgument("input");
            task.setTitle((String) input.get("title"));

            if(input.containsKey("description"))
                task.setDescription((String) input.get("description"));
            if(input.containsKey("priority"))
                task.setPriority(Integer.parseInt((String) input.get("priority")));
            if(input.containsKey("list"))
                task.setList(lists.findById((Integer) input.get("list")).orElse(null));
            if(input.containsKey("done"))
                task.setDone(Boolean.parseBoolean((String) input.get("done")));

            tasks.save(task);
            return task;
        };
    }

    public DataFetcher deleteOne() {
        return env -> {
            Integer id = Integer.parseInt(env.getArgument("id"));
            tasks.deleteById(id);
            return true;
        };
    }

    public DataFetcher modifyOne() {
        return env -> {
            Map<String, Object> input = env.getArgument("input");
            Integer id = Integer.parseInt((String) input.get("id"));
            Task task = tasks.findById(id).orElse(null);

            if(task != null) {
                if(input.containsKey("title"))
                    task.setTitle((String) input.get("title"));
                if(input.containsKey("description"))
                    task.setDescription((String) input.get("description"));
                if(input.containsKey("priority"))
                    task.setPriority((Integer) input.get("priority"));
                if(input.containsKey("list")) {
                    List list = lists.findById((Integer) input.get("list")).orElse(null);
                    if(list != null)
                        task.setList(list);
                }
                if(input.containsKey("done"))
                    task.setDone((Boolean) input.get("done"));

                tasks.save(task);
                return task;
            } else return null;
        };
    }

    public DataFetcher truncate() {
        return _env -> {
            tasks.deleteAll();
            return true;
        };
    }
}
