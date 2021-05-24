package io.pgp.justnow.graphql.wiring;

import graphql.schema.idl.TypeRuntimeWiring;
import io.pgp.justnow.graphql.fetcher.TaskFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class TaskAggregateResultWiring {

    @Autowired
    private TaskFetcher tasks;

    public TypeRuntimeWiring.Builder getBuilder() {
        return newTypeWiring("TaskAggregateResult")
                .dataFetcher("count", tasks.getCount());
    }
}
