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
public class ListFetcher {
    @Autowired
    private ListRepository lists;

    @Autowired
    private TaskRepository tasks;

    public DataFetcher getAll() {
        return _env -> lists.findAll();
    }

    public DataFetcher getOne() {
        return env -> lists.findById(Integer.parseInt(env.getArgument("id")));
    }

    public DataFetcher getCount() {
        return env -> {
            java.util.List<List> lists = env.getSource();
            return lists.size();
        };
    }

    public DataFetcher getListTasks() {
        return env -> {
            List list = env.getSource();
            return tasks.findAllByList(list);
        };
    }

    public DataFetcher addOne() {
        return env -> {
            List list = new List();
            Map<String, String> input = env.getArgument("input");

            list.setName(input.get("name"));
            list.setAuthor(input.get("author"));

            lists.save(list);
            return list;
        };
    }

    public DataFetcher deleteOne() {
        return env -> {
            Integer id = Integer.parseInt(env.getArgument("id"));
            lists.deleteById(id);
            return true;
        };
    }

    public DataFetcher modifyOne() {
        return env -> {
            Map<String, Object> input = env.getArgument("input");
            Integer id = Integer.parseInt((String) input.get("id"));
            List list = lists.findById(id).orElse(null);

            if(list != null) {
                if(input.containsKey("name"))
                    list.setName((String) input.get("name"));
                if(input.containsKey("author"))
                    list.setAuthor((String) input.get("author"));

                lists.save(list);
                return list;
            } else return null;
        };
    }

    public DataFetcher truncate() {
        return _env -> {
            lists.deleteAll();
            return true;
        };
    }
}
