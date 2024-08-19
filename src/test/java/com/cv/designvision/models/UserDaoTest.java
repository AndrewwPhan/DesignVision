package com.cv.designvision.models;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// @Disabled("Not to be run in CI with USE_DB true")
class UserDaoTest {

    public static final boolean USE_DB = false; // *** DO NOT PUSH WITH TRUE ***
    public static final String TEST_USERS_DB = "jdbc:sqlite:testUsers.db";

    IUserDAO dao;
    IUser fred = new User("Fred", "F", "e", "ph", "");

    private Connection connection = null;

    private void prepTestConnection() {
        if (connection != null) return;
        try {
            connection = DriverManager.getConnection(TEST_USERS_DB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void dropTables() {
        try {
            Statement dropTable = connection.createStatement();
            dropTable.execute("DROP TABLE IF EXISTS users");
            dropTable.execute("DROP TABLE IF EXISTS reviews");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        if (USE_DB) {
            prepTestConnection();
            dropTables();
            dao = new SqliteUserDao(connection);
        } else {
            dao = new MockUserDao();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        dao = null;
        if (connection != null) connection.close();
    }

    @Test
    void newTableIsEmpty() {
        // New table already created in setup
        assertThat(dao.getAllUsers()).isEmpty();
    }

    @Test
    void getAllGetsCorrectNumber() {
        // TODO: Method should be tested more fully than just this.
        //  Good hidey for bugs.
        int numOfContacts = 10;
        for (int i = 0; i < numOfContacts; i++)
            dao.createUser(new User().setEmail("test").setPassword("pass"));
        assertThat(dao.getAllUsers().size()).isEqualTo(numOfContacts);
    }

    @Test
    void addContactUpdatesIdInContact() {
        fred.setId(999); // known id to see change
        dao.createUser(fred);
        assertThat(fred.getId()).isOne();
    }

    @Test
    void addMultipleContactsIncrementsId() {
        dao.createUser(new User("Fred1", "F").setEmail("a").setPassword("a"));
        dao.createUser(new User("Fred2", "F").setEmail("a").setPassword("a"));
        dao.createUser(new User("Fred3", "F").setEmail("a").setPassword("a"));
        dao.createUser(new User("Fred4", "F").setEmail("a").setPassword("a"));
        dao.createUser(fred);
        assertThat(fred.getId()).isEqualTo(5);
    }

    @Test
    void getContactBuildsNewContactFromIdAlone() {
        dao.createUser(fred);
        IUser c1 = dao.readUser(1);
        // assertThat(c1).isNotEqualTo(fred); // no overridden equals
        assertThat(c1.getId()).isEqualTo(fred.getId());
        assertThat(c1.getFirstName()).isEqualTo(fred.getFirstName());
    }

    @Test
    void getNonExistentContactReturnsNullContact() {
        IUser ghost = dao.readUser(10);
        assertThat(ghost).isNull();
    }

    @Test
    void updatingContactDetailsKeepsId() {
        dao.createUser(fred);
        String fullLast = "Flintstone";
        fred.setLastName(fullLast);
        dao.updateUser(fred);
        IUser c1 = dao.readUser(fred.getId());
        assertThat(c1.getLastName()).isEqualTo(fullLast);
    }

    @Test
    void deletedContactShouldNoLongerBeInTable() {
        dao.createUser(new User("a","a").setEmail("a").setPassword("a"));
        dao.createUser(fred);
        dao.createUser(new User("b","b").setEmail("a").setPassword("a"));

        dao.deleteUser(fred);
        IUser ghostFred = dao.readUser(fred.getId());
        assertThat(ghostFred).isNull();
    }

    @Test
    void searchExistingUserByEmail_returnsUser() {
        IUser user = new User()
                .setFirstName("Bob")
                .setLastName("Smith")
                .setEmail("asfd@asdf")
                .setPassword("Aasdf1234@");
        dao.createUser(user);
        assertThat(dao.readUserByEmailAndPassword(user).getFullName())
                .isEqualTo(user.getFullName());
    }

    @Test
    void searchNonExistingUser_returnsNull() {
        IUser user = new User()
                .setEmail("notInDb")
                .setPassword("Aasdf1234@");
        assertThat(dao.readUserByEmailAndPassword(user)).isNull();
    }

    @Test
    void shouldThrowExceptionIfNoEmailAsRequiredParam(){
        Assertions.assertThrows(
                RuntimeException.class,
                () -> dao.createUser(new User().setPassword("pass"))
        );
    }

    @Test
    void shouldThrowExceptionIfNoPasswordAsRequiredParam(){
        Assertions.assertThrows(
                RuntimeException.class,
                () -> dao.createUser(new User().setEmail("email"))
        );
    }

    @Test
    void userFromDaoHasCorrectListOfReviews() {
        IUser user = new User()
                .setFirstName("a")
                .setEmail("a@b")
                .setPassword("ab");
        Review review = new Review();
        String expected = "111000111";
        review.setEncoded(expected);
        user.getReviews().add(review);
        dao.createUser(user);
        IUser dbUser = dao.readUser(1);
        assertThat(dbUser.getReviews().get(0).getEncoded())
                .isEqualTo(expected);
    }

    @Test
    void updatingUserResetsDataTable() {
        IUser user = new User()
                .setFirstName("a")
                .setEmail("email")
                .setPassword("pass");
        user.getReviews().clear();
        user.getReviews().add(new Review());
        dao.createUser(user);

        // Update user object
        String expected = "111000111";
        user.getReviews().get(0).setEncoded(expected);
        dao.updateUser(user);

        // Check users reviews are really updated in db
        IUser userFromDb = dao.readUserByEmailAndPassword(user);
        assertThat(userFromDb.getReviews().get(0).getEncoded())
                .isEqualTo(expected);
    }
}
