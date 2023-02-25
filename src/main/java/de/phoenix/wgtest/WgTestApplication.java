package de.phoenix.wgtest;

import de.phoenix.wgtest.model.management.*;
import de.phoenix.wgtest.model.security.EUserRole;
import de.phoenix.wgtest.model.security.User;
import de.phoenix.wgtest.model.security.UserRole;
import de.phoenix.wgtest.repository.management.*;
import de.phoenix.wgtest.repository.security.UserRepository;
import de.phoenix.wgtest.repository.security.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class WgTestApplication implements CommandLineRunner {

	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_RAW_PASSWORD = "12345678";
	private static final String ADMIN_EMAIL = "admin@mail.de";

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(WgTestApplication.class, args);
	}


	@Override
	public void run(String... args) throws IOException {
		initialize();
	}

	public void initialize() throws IOException {
		initializeUserRoles();
		initializeRoles();
		initializeAdminAccount();
		//createTestFiles();
	}

	public void initializeUserRoles() {
		//create user roles
		for (EUserRole role : EUserRole.values()) {
			if (userRoleRepository.findByName(role).isEmpty()) {
				userRoleRepository.save(new UserRole(role));
			}
		}
	}

	public void initializeRoles() {
		//create roles for person/institution
		for (ERole role : ERole.values()) {
			if (roleRepository.findByTypeAndSpecification(role, null).isEmpty()) {
				Role r = new Role();
				r.setType(role);
				roleRepository.save(r);
			}
		}
	}

	public void initializeAdminAccount() {
		//create user admin
		if (!userRepository.existsByUsername(ADMIN_USERNAME)) {
			User user = new User(ADMIN_USERNAME, ADMIN_EMAIL, encoder.encode(ADMIN_RAW_PASSWORD));
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(userRoleRepository.findByName(EUserRole.ROLE_ADMIN).orElse(null));
			user.setRoles(userRoles);
			userRepository.save(user);
		}
	}

	/*public void createTestFiles() throws IOException {
		//TODO hier dann mit den Files weiter machen!!!!
		Path path = Paths.get("C:\\Users\\Rico\\Desktop\\Test");
		List<Path> result = StreamEx.of(Files.walk(path)).toList();
		List<Path> dirs = StreamEx.of(result).filter(Files::isDirectory).toList();
		List<Path> files = StreamEx.of(result).filter(Files::isRegularFile).toList();

		System.out.println(dirs.size() + " directories found!\n");
		System.out.println("Directories:\n");
		dirs.forEach(System.out::println);

		System.out.println("\n");
		System.out.println(files.size() + " files found!\n");
		System.out.println("Files:\n");
		files.forEach(System.out::println);

		System.out.println("\n");
		System.out.println("\n");

		*//*File dir = new File("tmp/test");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File tmp = new File(dir, "tmp.txt");
		if (!tmp.exists()) {
			tmp.createNewFile();
		}*//*
		String jsonInString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(getNode(path));
		System.out.println(jsonInString);
	}

	public static Node getNode(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			return new Node(path.getFileName().toString(), path.toString(), "directory", getDirList(path));
		} else {
			return new Node(path.getFileName().toString(), path.toString(), "file", null);
		}
	}

	public static List<Node> getDirList(Path path) throws IOException {
		List<Node> nodeList = new ArrayList<>();
		for (Path p : Files.walk(path,1).collect(Collectors.toList())) {
			if (Files.isSameFile(p, path)) {
				continue;
			}
			nodeList.add(getNode(p));
		}
		return nodeList;
	}

	public static class Node {
		private String name;
		private String path;
		private String type;
		private List<Node> children;

		public Node(String name, String path, String type, List<Node> children) {
			this.name = name;
			this.path = path;
			this.type = type;
			this.children = children;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<Node> getChildren() {
			return children;
		}

		public void setChildren(List<Node> children) {
			this.children = children;
		}
	}*/
}
