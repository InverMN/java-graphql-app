package io.pgp.justnow.graphql.wiring;

import graphql.schema.DataFetcher;
import graphql.schema.idl.TypeRuntimeWiring;
import io.pgp.justnow.graphql.fetcher.TaskFetcher;
import io.pgp.justnow.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class TaskWiring {
    @Autowired
    private TaskFetcher tasks;

    public TypeRuntimeWiring.Builder getBuilder() {
        return newTypeWiring("Task")
                .dataFetcher("list", tasks.getTaskList());
    }
}
