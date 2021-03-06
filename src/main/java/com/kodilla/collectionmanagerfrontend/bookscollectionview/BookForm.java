package com.kodilla.collectionmanagerfrontend.bookscollectionview;

import com.kodilla.collectionmanagerfrontend.service.BackendApiService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Route
@Component
@UIScope
@Getter
@Setter
public class BookForm extends FormLayout {
    private MainView mainView;
    private BackendApiService backendApiService;
    private BookToBackendDto bookToBackendDto;
    private Long booksCollectionID;

    private final TextField bookId = new TextField("Nr pozycji");
    private final TextField isbn = new TextField("ISBN");
    private final TextField isbn13 = new TextField("ISBN13");
    private final TextField title = new TextField("Tytuł");
    private final TextField publisher = new TextField("Wydawnictwo");
    private final TextField synopsys = new TextField("Streszczenie");
    private final TextField image = new TextField("Link");
    private final TextField authors = new TextField("Autorzy");
    private final TextField subjects = new TextField("Kategorie");
    private final TextField publishDate = new TextField("Rok wydania");
    private final TextField booksCollectionId = new TextField("Nr kolekcji");

    private Button delete = new Button("Delete");
    private Button update = new Button("Update");

    private Binder<BookToBackendDto> binder;

    @Autowired
    public BookForm(BackendApiService backendApiService, @Lazy MainView mainView) {
        this.backendApiService = backendApiService;
        this.mainView = mainView;

        this.binder = new Binder<>(BookToBackendDto.class);
        HorizontalLayout buttons = new HorizontalLayout(update, delete);
        VerticalLayout formLayout = new VerticalLayout(bookId, isbn, isbn13, title, publisher, synopsys, image,
                authors, subjects, publishDate, booksCollectionId);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(formLayout, buttons);
        binder.bindInstanceFields(this);
        update.addClickListener(e -> updateBook());
        delete.addClickListener(e -> delete());
    }

    public void setBookToBackendDto(BookToBackendDto bookToBackendDto) {
        binder.setBean(bookToBackendDto);

        if (bookToBackendDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
            isbn.focus();
        }
    }

    public void delete() {
        bookToBackendDto = binder.getBean();
        backendApiService.deleteBook(Long.valueOf(bookToBackendDto.getBookId()));
        mainView.refresh(Long.valueOf(bookToBackendDto.getBooksCollectionId()));
    }

    public void updateBook() {
        bookToBackendDto = binder.getBean();
        backendApiService.updateBook(Long.valueOf(bookToBackendDto.getBooksCollectionId()), Long.valueOf(bookToBackendDto.getBookId()), bookToBackendDto);
        mainView.refresh(Long.valueOf(bookToBackendDto.getBooksCollectionId()));
    }
}
