package cbcoder.dealerwebapp.UsersInfo.model;

import cbcoder.dealerwebapp.UsersInfo.model.enums.RoleEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * Role entity class represents the roles that a user can have in the system.
 * The roles are defined in the RoleEnum class.
 * The class is annotated with @Entity to indicate that it is an entity class.
 * The @Table annotation is used to specify the name of the table in the database.
 * The class is also annotated with @SequenceGenerator to generate the primary key values.
 * The class implements the Serializable interface to make the object serializable.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see RoleEnum
 * @since 2024-06-15
 */
@Entity
@Table(name = "ROLES")
@SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @Column(name = "ROLE_ID", updatable = false, nullable = false)
    private Integer roleId;

    @Column(name = "ROLE_NAME")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;


    public Role() {
    }

    public Role(RoleEnum roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public RoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleEnum roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(getRoleId(), role.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRoleId());
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName=" + roleName +
                '}';
    }
}
