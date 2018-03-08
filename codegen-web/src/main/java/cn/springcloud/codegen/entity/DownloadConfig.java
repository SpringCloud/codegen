package cn.springcloud.codegen.entity;

/**
 * @author Vincent.
 * @createdOn 2018/03/07 23:12
 */
public class DownloadConfig {
    private String pomArtifactId;
    private String moduleName;
    private String pomName;
    private String springBootVersion;
    private String javaVersion;
    private String basePackage;
    private String scAloneRadio;

    public String getPomArtifactId() {
        return pomArtifactId;
    }

    public void setPomArtifactId(String pomArtifactId) {
        this.pomArtifactId = pomArtifactId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPomName() {
        return pomName;
    }

    public void setPomName(String pomName) {
        this.pomName = pomName;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getScAloneRadio() {
        return scAloneRadio;
    }

    public void setScAloneRadio(String scAloneRadio) {
        this.scAloneRadio = scAloneRadio;
    }
}
