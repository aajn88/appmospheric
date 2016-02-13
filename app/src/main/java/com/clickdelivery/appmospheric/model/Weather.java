package com.clickdelivery.appmospheric.model;

/**
 * The weather information
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class Weather {

    /** Weather Information Id **/
    private Integer id;

    /** Main weather information **/
    private String main;

    /** Weather description **/
    private String description;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the main
     */
    public String getMain() {
        return main;
    }

    /**
     * @return main the main to set
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
