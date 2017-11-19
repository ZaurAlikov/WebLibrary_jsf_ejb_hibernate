package ru.alikovzaur.library.entityes;

import javax.persistence.*;

@Entity
@Table(name = "book", schema = "library")
public class BookEntity {
    private long id;
    private String name;
    private byte[] content;
    private int pageCount;
    private String isbn;
    private int publishYear;
    private byte[] image;
    private String descr;
    private AuthorEntity author;
    private GenreEntity genre;
    private PublisherEntity publisher;
    private int rating;
    private int countVoice;

//    @PostLoad
//    private void calcRating(){
//        List<RatingEntity> ratingEntities1 = ratingEntities.stream().filter(x -> x.getRating() > 0).collect(Collectors.toList());
//        countVoice = ratingEntities1.size();
//        rating = (int) Math.round(ratingEntities1.stream().mapToInt(RatingEntity::getRating).average().orElse(0));
////        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
////        FacesContext facesContext = FacesContext.getCurrentInstance();
////        String username = facesContext.getExternalContext().getUserPrincipal().getName();
////        readOnly = ratingEntities.stream().anyMatch(r -> r.getUsername().equals(username));
//    }

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", nullable = false)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Basic
    @Column(name = "page_count", nullable = false)
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Basic
    @Column(name = "isbn", nullable = false, length = 100)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "publish_year", nullable = false)
    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", nullable = false)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Basic
    @Column(name = "descr", nullable = false, length = 5000)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public AuthorEntity getAuthor() {
        return this.author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @ManyToOne
    @JoinColumn(name = "genre_id")
    public GenreEntity getGenre() {
        return this.genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    public PublisherEntity getPublisher() {
        return this.publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "rating", nullable = false)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "count_voice", nullable = false)
    public int getCountVoice() {
        return countVoice;
    }

    public void setCountVoice(int countVoice) {
        this.countVoice = countVoice;
    }
}
