package model;

public class ITCompany extends Business{
    public ITCompany() {
        WorkerTeam development = new WorkerTeam("Development");
        development.add(new DevOps());
        development.add(new DevOps());
        development.add(new FullStackEngineer());
        development.add(new FullStackEngineer());
        development.add(new TechnicalArchitect());
        development.add(new Designer());

        // Composite: team inside a team

        WorkerTeam product = new WorkerTeam("Product team");
        product.add(development).add(new BusinessAnalyst()).add(new ProductManager());

        addWorker(product);
    }
}
