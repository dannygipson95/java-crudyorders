package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Agent;
import org.springframework.transaction.annotation.Transactional;

public interface AgentService {
    Agent findByAgentId(long id);

    Agent save(Agent agent);
}
