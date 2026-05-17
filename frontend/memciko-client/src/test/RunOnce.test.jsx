import { describe, test, expect } from "vitest";
import { fireEvent, render, screen } from "@testing-library/react";
import { Provider } from "react-redux";
import store from "../app/store.jsx";
import App from "../App";

describe("Run Once", () => {

  test("toggles run once checkbox", () => {
    render(
      <Provider store={store}>
        <App />
      </Provider>
    );

    const checkbox = screen.getByRole("checkbox", {
      name: /Run Once/i
    });

    fireEvent.click(checkbox);

    expect(checkbox.checked).toBe(true);
  });

});