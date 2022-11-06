package seedu.address.logic.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_EXAM;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EXAMONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EXAMTWO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MODULEONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MODULETWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertTasksHaveSameExamSuccess;
import static seedu.address.logic.commands.EditExamCommand.MESSAGE_EXAM_NOT_EDITED;
import static seedu.address.logic.commands.EditModuleCommand.MESSAGE_TASKS_EXAMS_RELATED_MODIFIED;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXAM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FORTH_EXAM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTEENTH_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXAM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EXAM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRTEENTH_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWELVE_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exam.Exam;
import seedu.address.model.exam.ExamDate;
import seedu.address.model.exam.ExamDescription;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleCredit;
import seedu.address.model.module.ModuleName;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditExamDescriptorBuilder;
import seedu.address.testutil.EditModuleDescriptorBuilder;
import seedu.address.testutil.ExamBuilder;
import seedu.address.testutil.ModuleBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for EditModuleCommand.
 */
public class EditModuleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListWithModuleUnrelatedToAnyTaskAndExam_success() {
        Module editedModule = new ModuleBuilder(new Module(new ModuleCode("CS3213"),
                new ModuleName("Final Examinations"), new ModuleCredit(4))).build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand =
                new EditModuleCommand(INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(model.getFilteredModuleList()
                .get(INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM.getZeroBased()), editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredListWithModuleUnrelatedToAnyTaskAndExam_success() {
        Module editedModule = new ModuleBuilder().withModuleCode("CS3213").withModuleName("Final Examinations").build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand =
                new EditModuleCommand(INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(model.getFilteredModuleList()
                .get(INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM.getZeroBased()), editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }

    public void linkHelper(Model model, Index examIndex, Index taskIndex) throws CommandException {
        LinkExamCommand linkExamCommand = new LinkExamCommand(examIndex, taskIndex);
        linkExamCommand.execute(model);
    }

    @Test
    public void execute_allFieldsSpecifiedWithModuleRelatedToTasksNotExams_success() {
        Module moduleToEdit = model.getFilteredModuleList().get(INDEX_FOURTH_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleToEdit).withModuleCode("CS3213")
                .withModuleName("Programming").withModuleCredit(5).build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FOURTH_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit())
                + MESSAGE_TASKS_EXAMS_RELATED_MODIFIED;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(moduleToEdit, editedModule);
        expectedModel.updateModuleFieldForTask(moduleToEdit, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedWithModuleRelatedToTasksNotExams_success() {
        Module moduleToEdit = model.getFilteredModuleList().get(INDEX_FOURTH_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleToEdit).withModuleCode("CS3213")
                .withModuleName("Programming").build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FOURTH_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit())
                + MESSAGE_TASKS_EXAMS_RELATED_MODIFIED;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(moduleToEdit, editedModule);
        expectedModel.updateModuleFieldForTask(moduleToEdit, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }

    @Test
    public void execute_moduleCodeFieldUnspecifiedWithModuleRelatedToTasksNotExams_success() {
        Module moduleToEdit = model.getFilteredModuleList().get(INDEX_FOURTH_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleToEdit).withModuleName("Programming").withModuleCredit(5).build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FOURTH_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(moduleToEdit, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedWithModuleRelatedToTasksAndExams_success() {
        Module moduleToEdit = model.getFilteredModuleList().get(INDEX_SIXTH_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleToEdit).withModuleCode("CS3213")
                .withModuleName("Programming").withModuleCredit(5).build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_SIXTH_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit())
                + MESSAGE_TASKS_EXAMS_RELATED_MODIFIED;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(moduleToEdit, editedModule);
        expectedModel.updateModuleFieldForTask(moduleToEdit, editedModule);
        expectedModel.updateModuleFieldForExam(moduleToEdit, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedWithModuleRelatedToTasksAndExams_success() {
        Module moduleToEdit = model.getFilteredModuleList().get(INDEX_SIXTH_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleToEdit).withModuleCode("CS3213").withModuleCredit(5).build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_SIXTH_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit())
                + MESSAGE_TASKS_EXAMS_RELATED_MODIFIED;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(moduleToEdit, editedModule);
        expectedModel.updateModuleFieldForTask(moduleToEdit, editedModule);
        expectedModel.updateModuleFieldForExam(moduleToEdit, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }

    @Test
    public void execute_moduleCodeFieldUnspecifiedWithModuleRelatedToTasksAndExams_success() {
        Module moduleToEdit = model.getFilteredModuleList().get(INDEX_SIXTH_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleToEdit).withModuleName("Programming").withModuleCredit(5).build();
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_SIXTH_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.getModuleCode(), editedModule.getModuleName(), editedModule.getModuleCredit());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.replaceModule(moduleToEdit, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
        assertTasksHaveSameExamSuccess(model, expectedModel);
    }
//
//    //tasks link to that exam but exam field edits only the description and date, module remains same.
//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredListWithTasksLinkedToTheSpecificExam_success()
//            throws CommandException {
//        Exam editedExam = new ExamBuilder(new Exam(new Module(new ModuleCode("CS2030S")),
//                new ExamDescription("Final Exam"), new ExamDate("01-11-2023"))).build();
//        EditExamCommand.EditExamDescriptor descriptor = new EditExamDescriptorBuilder(editedExam).build();
//        EditExamCommand editExamCommand = new EditExamCommand(INDEX_THIRD_EXAM, descriptor);
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_FOURTEENTH_TASK);
//        String expectedMessage = String.format(EditExamCommand.MESSAGE_EDIT_EXAM_SUCCESS, editedExam);
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        linkHelper(expectedModel, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        linkHelper(expectedModel, INDEX_THIRD_EXAM, INDEX_FOURTEENTH_TASK);
//        Exam exam = model.getFilteredExamList().get(2);
//        expectedModel.replaceExam(exam, editedExam, false);
//        expectedModel.updateExamFieldForTask(exam, editedExam);
//        assertCommandSuccess(editExamCommand, model, expectedMessage, expectedModel);
//        assertTasksHaveSameExamSuccess(model, expectedModel);
//    }
//
//
//    @Test
//    public void execute_changeModuleWithTasksLinkToExam_success() throws CommandException {
//        Exam editedExam = new ExamBuilder(new Exam(new Module(new ModuleCode("CS2040s")),
//                new ExamDescription("Exam one"), new ExamDate("20-08-2023"))).build();
//        EditExamCommand.EditExamDescriptor descriptor = new EditExamDescriptorBuilder(editedExam).build();
//        EditExamCommand editExamCommand = new EditExamCommand(INDEX_THIRD_EXAM, descriptor);
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_FOURTEENTH_TASK);
//        linkHelper(model, INDEX_FORTH_EXAM, INDEX_THIRTEENTH_TASK);
//        String expectedMessage = String.format(EditExamCommand.MESSAGE_EDIT_EXAM_SUCCESS + "\n"
//                + "Warning! All the tasks previously linked to this exam are now unlinked.", editedExam);
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        linkHelper(expectedModel, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        linkHelper(expectedModel, INDEX_THIRD_EXAM, INDEX_FOURTEENTH_TASK);
//        linkHelper(expectedModel, INDEX_FORTH_EXAM, INDEX_THIRTEENTH_TASK);
//        Exam exam = model.getFilteredExamList().get(2);
//        expectedModel.replaceExam(exam, editedExam, false);
//        expectedModel.unlinkTasksFromExam(exam);
//        expectedModel.updateExamFieldForTask(exam, editedExam);
//        assertCommandSuccess(editExamCommand, model, expectedMessage, expectedModel);
//        assertTasksHaveSameExamSuccess(model, expectedModel);
//    }
//
//    @Test
//    public void execute_allFieldsChangeWithTasksLinkToExam_success() throws CommandException {
//        Exam editedExam = new ExamBuilder(new Exam(new Module(new ModuleCode("CS2040s")),
//                new ExamDescription("Midterm Paper"), new ExamDate("28-12-2023"))).build();
//        EditExamCommand.EditExamDescriptor descriptor = new EditExamDescriptorBuilder(editedExam).build();
//        EditExamCommand editExamCommand = new EditExamCommand(INDEX_THIRD_EXAM, descriptor);
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_FOURTEENTH_TASK);
//        linkHelper(model, INDEX_FORTH_EXAM, INDEX_THIRTEENTH_TASK);
//        String expectedMessage = String.format(EditExamCommand.MESSAGE_EDIT_EXAM_SUCCESS + "\n"
//                + "Warning! All the tasks previously linked to this exam are now unlinked.", editedExam);
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        linkHelper(expectedModel, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        linkHelper(expectedModel, INDEX_THIRD_EXAM, INDEX_FOURTEENTH_TASK);
//        linkHelper(expectedModel, INDEX_FORTH_EXAM, INDEX_THIRTEENTH_TASK);
//        Exam exam = model.getFilteredExamList().get(2);
//        expectedModel.replaceExam(exam, editedExam, false);
//        expectedModel.unlinkTasksFromExam(exam);
//        expectedModel.updateExamFieldForTask(exam, editedExam);
//        assertCommandSuccess(editExamCommand, model, expectedMessage, expectedModel);
//        assertTasksHaveSameExamSuccess(model, expectedModel);
//    }
//
//    //no tasks link to the exam but exam changed to be duplicate.
//    @Test
//    public void execute_duplicateExamUnfilteredList_failure() {
//        Exam exam = model.getFilteredExamList().get(INDEX_THIRD_EXAM.getZeroBased());
//        EditExamCommand.EditExamDescriptor descriptor = new EditExamDescriptorBuilder(exam).build();
//        EditExamCommand editExamCommand = new EditExamCommand(INDEX_FORTH_EXAM, descriptor);
//        assertCommandFailure(editExamCommand, model, MESSAGE_DUPLICATE_EXAM);
//    }
//
//    @Test
//    public void execute_duplicateExamUnfilteredListWithTasksLinked_failure() throws CommandException {
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        Exam exam = model.getFilteredExamList().get(INDEX_THIRD_EXAM.getZeroBased());
//        EditExamCommand.EditExamDescriptor descriptor = new EditExamDescriptorBuilder(exam).build();
//        EditExamCommand editExamCommand = new EditExamCommand(INDEX_FORTH_EXAM, descriptor);
//        assertCommandFailure(editExamCommand, model, MESSAGE_DUPLICATE_EXAM);
//        assertTrue(model.getFilteredTaskList().get(11).getExam().isSameExam(exam));
//    }
//
//    @Test
//    public void execute_editExamToANonExistingModule_failure() throws CommandException {
//        Exam exam = new ExamBuilder(new Exam(new Module(new ModuleCode("XX2000s")),
//                new ExamDescription("Midterm Paper"), new ExamDate("28-12-2023"))).build();
//        EditExamCommand.EditExamDescriptor descriptor = new EditExamDescriptorBuilder(exam).build();
//        EditExamCommand editExamCommand = new EditExamCommand(INDEX_THIRD_EXAM, descriptor);
//        linkHelper(model, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        linkHelper(expectedModel, INDEX_THIRD_EXAM, INDEX_TWELVE_TASK);
//        String expectedMessage = "This module does not exist";
//        assertThrows(CommandException.class, expectedMessage, () -> editExamCommand.execute(model));
//        assertEquals(expectedModel, model);
//        assertTasksHaveSameExamSuccess(model, expectedModel);
//    }
//
//    //no fields change
//    @Test
//    public void execute_noFieldChangeUnfilteredList_failure() {
//        Exam exam = model.getFilteredExamList().get(INDEX_THIRD_EXAM.getZeroBased());
//        EditExamCommand.EditExamDescriptor descriptor = new EditExamDescriptorBuilder(exam).build();
//        EditExamCommand editExamCommand = new EditExamCommand(INDEX_THIRD_EXAM, descriptor);
//        assertCommandFailure(editExamCommand, model, MESSAGE_EXAM_NOT_EDITED);
//    }
//
//    @Test
//    public void execute_invalidExamIndexUnfilteredList_failure() {
//        int index = model.getFilteredExamList().size() + 1;
//        Index outOfBoundIndex = Index.fromOneBased(index);
//        EditExamCommand.EditExamDescriptor descriptor =
//                new EditExamDescriptorBuilder().withDescription("Final exam paper").build();
//        EditExamCommand editExamCommand = new EditExamCommand(outOfBoundIndex, descriptor);
//        assertCommandFailure(editExamCommand, model,
//                String.format(Messages.MESSAGE_INVALID_EXAM_INDEX_TOO_LARGE, index));
//    }

    @Test
    public void equals() {
        final EditModuleCommand command =
                new EditModuleCommand(INDEX_SECOND_MODULE, DESC_MODULEONE);

        // same values -> returns true
        EditModuleCommand.EditModuleDescriptor copyDescriptor =
                new EditModuleCommand.EditModuleDescriptor(DESC_MODULEONE);
        EditModuleCommand commandWithSameValues =
                new EditModuleCommand(INDEX_SECOND_MODULE, copyDescriptor);
        assertTrue(command.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different types -> returns false
        assertFalse(command.equals(10));

        // different index -> returns false
        assertFalse(command.equals(new EditModuleCommand(INDEX_THIRD_MODULE, DESC_MODULEONE)));

        // different descriptor -> returns false
        assertFalse(command.equals(new EditModuleCommand(INDEX_SECOND_MODULE, DESC_MODULETWO)));
    }
}
