import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

const API_URL = "http://localhost:8080/api/tasks";

const emptyForm = {
  taskKey: "",
  name: "",
  description: "",
  cronExpression: "",
  active: true,
  editable: true,
  runOnce: false,
  startAt: "",
  parameters: {
    message: ""
  }
};

export const fetchTasks = createAsyncThunk("tasks/fetchTasks", async () => {
  const response = await fetch(API_URL);
 
  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  return await response.json();
});

export const saveTask = createAsyncThunk(
  "tasks/saveTask",
  async ({ form, editingId }) => {
    const payload = {
      ...form,
      taskKey: form.taskKey || null,
      cronExpression: form.runOnce ? null : form.cronExpression,
      startAt: form.runOnce ? form.startAt : null
    };

    const url = editingId ? `${API_URL}/${editingId}` : API_URL;
    const method = editingId ? "PUT" : "POST";

    await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });
  }
);

export const deleteTask = createAsyncThunk("tasks/deleteTask", async (id) => {
  await fetch(`${API_URL}/${id}`, { method: "DELETE" });
  return id;
});

const tasksSlice = createSlice({
  name: "tasks",
  initialState: {
    items: [],
    form: emptyForm,
    editingId: null,
    status: "idle",
    error: null
  },
  reducers: {
    updateFormField(state, action) {
      const { name, value } = action.payload;
      state.form[name] = value;
    },
    updateMessage(state, action) {
      state.form.parameters.message = action.payload;
    },
    editTask(state, action) {
      const task = action.payload;
      state.editingId = task.id;
      state.form = {
        taskKey: task.taskKey || "",
        name: task.name || "",
        description: task.description || "",
        cronExpression: task.cronExpression || "",
        active: task.active,
        editable: task.editable,
        runOnce: task.runOnce,
        startAt: task.startAt || "",
        parameters: {
          message: task.parameters?.message || ""
        }
      };
    },
    resetForm(state) {
      state.form = emptyForm;
      state.editingId = null;
      state.error = null;
    },
    setError(state, action) {
      state.error = action.payload;
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchTasks.fulfilled, (state, action) => {
        state.items = action.payload;
      })
      .addCase(saveTask.fulfilled, (state) => {
        state.form = emptyForm;
        state.editingId = null;
      })
      .addCase(deleteTask.fulfilled, (state, action) => {
        state.items = state.items.filter((task) => task.id !== action.payload);
      });
  }
});

export const {
  updateFormField,
  updateMessage,
  editTask,
  resetForm,
  setError
} = tasksSlice.actions;

export default tasksSlice.reducer;