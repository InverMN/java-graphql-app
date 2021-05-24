package io.pgp.justnow.graphql.wiring;

import graphql.schema.idl.TypeRuntimeWiring;
import io.pgp.justnow.graphql.fetcher.ListFetcher;
import io.pgp.justnow.graphql.fetcher.TaskFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class MutationWiring {

    @Autowired
    private TaskFetcher tasks;

    @Autowired
    private ListFetcher lists;

    public TypeRuntimeWiring.Builder getBuilder() {
        return newTypeWiring("Mutation")
                .dataFetcher("addTask", tasks.addOne())
                .dataFetcher("deleteTask", tasks.deleteOne())
                .dataFetcher("modifyTask", tasks.modifyOne())
                .dataFetcher("truncateTasks", tasks.truncate())

                .dataFetcher("addList", lists.addOne())
                .dataFetcher("deleteList", lists.deleteOne())
                .dataFetcher("modifyList", lists.modifyOne())
                .dataFetcher("truncateLists", lists.truncate());
    }
}
