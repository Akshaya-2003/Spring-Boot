package dev.danvega.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    // Find all runs
    public List<Run> findAll(){
        return jdbcClient.sql("SELECT * FROM run")
                .query(Run.class)
                .list();
    }

    // Find a run by ID
    public Optional<Run> findById(Integer id){
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    // Create a new run
    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) VALUES(?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();
        Assert.state(updated == 1, "Failed to create run: " + run.title());
    }

    // Update an existing run
    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE Run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), run.id()))
                .update();
        Assert.state(updated == 1, "Failed to update run with ID: " + run.id());
    }

    // Delete a run by ID
    public void delete(Integer id) {
        var deleted = jdbcClient.sql("DELETE FROM Run WHERE id = ?")
                .param(1, id)
                .update();
        Assert.state(deleted == 1, "Failed to delete run with ID: " + id);
    }

    public int count(){ return jdbcClient.sql("select * from run").query().listOfRows().size();}

    public void saveAll(List<Run> runs){
        runs.stream().forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from run where location = ?")
                .param(1, location)  // Using positional parameter (1st parameter)
                .query(Run.class)
                .list();
    }
}

// private List<Run> runs = new ArrayList<>();
//    List<Run> findAll(){
//        return runs;
//    }
//
//    Optional<Run> findByID(Integer id){
//        return runs.stream()
//                .filter(run -> run.id() == id)
//                .findFirst();
//    }
//
//    void create(Run run){
//        runs.add(run);
//    }
//
//    void update(Run run, Integer id){
//        Optional<Run> existingRun = findByID(id);
//        if(existingRun.isPresent()){
//            runs.set(runs.indexOf(existingRun.get()),run);
//        }
//    }
//
//    void delete(Integer id){
//        runs.removeIf(run -> run.id().equals(id));
//    }
//
//
//    @PostConstruct
//    private void init() {
//        runs.add(new Run(1,
//                "Monday Morning Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
//                3,
//                Location.INDOOR));
//        runs.add(new Run(2,
//                "Wednesday Evening Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
//                6,
//                Location.INDOOR));
//    }
//}
