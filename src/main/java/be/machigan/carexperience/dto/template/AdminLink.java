package be.machigan.carexperience.dto.template;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminLink {
    public static final List<AdminLink> LINKS = List.of(
            new AdminLink("Gérer les utilisateurs", "/admin/users", "Ajouter ou supprimer des utilisateurs."),
            new AdminLink("Gérer mon compte", "/admin/users", "Modifier mes informations."),
            new AdminLink("Gérer les catégories", "/admin/categories", "Ajouter, modifier ou supprimer des catégories."),
            new AdminLink("Gérer les voitures", "/admin/cars", "Ajouter, modifier ou supprimer des voitures.")
    );

    private String name;
    private String link;
    private String description;
}
