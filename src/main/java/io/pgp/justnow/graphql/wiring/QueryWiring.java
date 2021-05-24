package io.pgp.justnow.graphql.wiring;

import graphql.schema.idl.TypeRuntimeWiring;
import io.pgp.justnow.graphql.fetcher.ListFetcher;
import io.pgp.justnow.graphql.fetcher.TaskFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class QueryWiring {
    @Autowired
    private TaskFetcher tasks;

    @Autowired
    private ListFetcher lists;

    public TypeRuntimeWiring.Builder getBuilder() {
        return newTypeWiring("Query")
                .dataFetcher("allTasks", tasks.getAll())
                .dataFetcher("aggregateTask", tasks.getAll())
                .dataFetcher("findTask", tasks.getOne())

                .dataFetcher("allLists", lists.getAll())
                .dataFetcher("aggregateList", lists.getAll())
                .dataFetcher("findList", lists.getOne());
    }
}
