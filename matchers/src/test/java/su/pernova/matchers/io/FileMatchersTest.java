package su.pernova.matchers.io;

import static su.pernova.matchers.core.EqualsMatcher.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import su.pernova.matchers.AbstractMatcherTest;
import su.pernova.matchers.Matcher;
import org.junit.jupiter.api.BeforeEach;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileMatchersTest extends AbstractMatcherTest {

	private File directory;
	private File file;

	@BeforeEach
	protected void setUp() throws IOException {
		directory = File.createTempFile("myDir", "");
		assertTrue(directory.delete(), "deleting " + directory);
		assertTrue(directory.mkdirs(), "mkdir " + directory);

		file = new File(directory, "myFile");
		file.createNewFile();
	}

	public void testAnExistingDirectory() {
		assertMatches("matches existing directory", FileMatchers.anExistingDirectory(), directory);
		assertDoesNotMatch("doesn't match existing file", FileMatchers.anExistingDirectory(), file);
		assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingDirectory(), new File("foo"));
	}

	public void testAnExistingFileOrDirectory() {
		assertMatches("matches existing file", FileMatchers.anExistingFileOrDirectory(), file);
		assertMatches("matches existing directory", FileMatchers.anExistingFileOrDirectory(), directory);
		assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingFileOrDirectory(), new File("foo"));
	}

	public void testAnExistingFile() {
		assertMatches("matches existing file", FileMatchers.anExistingFile(), file);
		assertDoesNotMatch("doesn't match existing directory", FileMatchers.anExistingFile(), directory);
		assertDoesNotMatch("doesn't match missing file", FileMatchers.anExistingFile(), new File("foo"));
	}

	public void testAReadableFile() { // Not all OSes will allow setting readability so have to be forgiving here.
		file.setReadable(true);
		assertMatches("matches readable file", FileMatchers.aReadableFile(), file);

		if (file.setReadable(false)) {
			assertDoesNotMatch("doesn't match unreadable file", FileMatchers.aReadableFile(), file);
		}
	}

	public void testAWritableFile() {
		assertMatches("matches writable file", FileMatchers.aWritableFile(), file);

		assertTrue(file.setWritable(false), "set writable off " + file);
		assertDoesNotMatch("doesn't match unwritable file", FileMatchers.aWritableFile(), file);
	}

	public void testAFileWithSizeLong() {
		assertMatches("matches file size", FileMatchers.aFileWithSize(0L), file);
		assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(34L), file);
	}

	public void testAFileWithSizeMatcherOfLong() {
		assertMatches("matches file size", FileMatchers.aFileWithSize(equalTo(0L)), file);
		assertDoesNotMatch("doesn't match incorrect file size", FileMatchers.aFileWithSize(equalTo(23L)), file);
	}

	public void testAFileNamed() {
		assertMatches("matches file name", FileMatchers.aFileNamed(equalTo(file.getName())), file);
		assertDoesNotMatch("doesn't match incorrect file name", FileMatchers.aFileNamed(equalTo("foo")), file);
	}

	public void testAFileWithCanonicalPath() throws Exception {
		assertMatches("matches file canonical path", FileMatchers.aFileWithCanonicalPath(equalTo(file.getCanonicalPath())), file);
		assertDoesNotMatch("doesn't match incorrect canonical path", FileMatchers.aFileWithCanonicalPath(equalTo("foo")), file);
	}

	public void testAFileWithAbsolutePath() {
		assertMatches("matches file absolute path", FileMatchers.aFileWithAbsolutePath(equalTo(file.getAbsolutePath())), file);
		assertDoesNotMatch("doesn't match incorrect absolute path", FileMatchers.aFileWithAbsolutePath(equalTo("foo")), file);
	}

	@Override
	protected Matcher<?> createMatcher() {
		return FileMatchers.aFileWithSize(1L);
	}

}
