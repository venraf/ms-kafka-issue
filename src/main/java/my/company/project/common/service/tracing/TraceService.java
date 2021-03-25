package my.company.project.common.service.tracing;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceService {

    private final Tracer tracer;

    @Autowired
    public TraceService(Tracer tracer) {
        this.tracer = tracer;
    }

    public String getCurrentTraceId(){
        return tracer.currentSpan().context().traceIdString();
    }

    public String getCurrentSpanId(){
        return tracer.currentSpan().context().spanIdString();
    }

}
