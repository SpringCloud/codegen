package cn.springcloud.codegen.entity;


public class ProjectModel {

    private String proType;
    private String language;
    private String springBootVersion;
    private String groupId;
    private String pomArtifactId;
    private String projectName;
    private String basePackage;
    private String javaVersion;
    private boolean isDockerTemplate;

    private Metadata metadata;

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isDockerTemplate() {
        return isDockerTemplate;
    }

    public void setDockerTemplate(boolean dockerTemplate) {
        isDockerTemplate = dockerTemplate;
    }

    public String getPomArtifactId() {
        return pomArtifactId;
    }

    public void setPomArtifactId(String pomArtifactId) {
        this.pomArtifactId = pomArtifactId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
