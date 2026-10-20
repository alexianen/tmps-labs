public class BusinessMaker {

    public Business createBusiness(String name, String location, int numWorkers, float revenue) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Business name is required.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Business location is required.");
        }
        if (numWorkers < 0) {
            throw new IllegalArgumentException("Number of workers cannot be negative.");
        }
        if (revenue < 0) {
            throw new IllegalArgumentException("Revenue cannot be negative.");
        }

        Business business = new Business();
        business.setName(name);
        business.setLocation(location);
        business.setNumWorkers(numWorkers);
        business.setRevenue(revenue);

        return business;
    }
}