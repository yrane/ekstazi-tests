class Main {
    public static void main(String[] args) {
        // process....("java -javaagent:...ekstazi..")
        if (org.ekstazi.Ekstazi.inst().checkIfAffected("name")) {
            org.ekstazi.Ekstazi.inst().startCollectingDependencies("name");
            try {
                org.junit.runner.JUnitCore.main(new String[]{"T"});
            } finally {
                org.ekstazi.Ekstazi.inst().finishCollectingDependencies("name");
            }
        }
    }
}
