package core;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SourceCodeCollector {
	public interface FileHandler {
		void handle(int level, String path, File file);
	}

	private interface Filter {
		boolean interested(int level, String path, File file);
	}

	private FileHandler fileHandler;
	private Filter filter;

	public SourceCodeCollector(Filter filter, FileHandler fileHandler) {
		this.filter = filter;
		this.fileHandler = fileHandler;
	}

	public void explore(File root) {
		explore(0, "", root);
	}

	private void explore(int level, String path, File file) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				explore(level + 1, path + "/" + child.getName(), child);
			}
		} else {
			if (filter.interested(level, path, file)) {
				fileHandler.handle(level, path, file);
			}
		}
	}

	public static List<File> listClasses(File projectDir) {
		List<File> codeFiles = new LinkedList<File>();
		new SourceCodeCollector((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			codeFiles.add(file);
		}).explore(projectDir);
		return codeFiles;
	}
}