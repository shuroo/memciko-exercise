import { describe, test, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import { Provider } from "react-redux";
import store from "../app/store.jsx";

import App from "../App";

describe("App", () => {
  test("renders task scheduler title", () => {
    render(
      <Provider store={store}>
        <App />
      </Provider>
    );

    const title = screen.getByRole("heading", {
      name: /task scheduler/i,
    });

    expect(title).toBeInTheDocument();
  });
});