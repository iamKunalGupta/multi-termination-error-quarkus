package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import org.jboss.resteasy.reactive.RestStreamElementType;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/test")
public class MultiResource {

    record Result(String id) {};

    @POST
    @Path("/multi")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<Result> testMulti(List<String> ids) {
        Set<String> existingIDs = Set.of("1", "2");
        return Multi.createFrom().emitter((c) -> {
            Log.info("For ids: " + ids);
            Log.info("Trying to locate in : " + existingIDs);
            for (String id: ids) {
                if (existingIDs.contains(id)) {
                    Log.info("Found id: " + id);
                    c.emit(new Result(id));
                    Log.info("Emitted item for id: " + id);
                }
                else {
                    Log.warn("Not found id: " + id);
                    c.fail(new IllegalArgumentException("Illegal Id: " + id));
                }
            }
            c.onTermination(() -> Log.info("Finished multi emitting"));
            c.complete();
        });
    }
}