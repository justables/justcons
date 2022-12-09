const colors = require("tailwindcss/colors");

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      colors: {
        base: { ...colors.gray },
        primary: colors.blue,
        danger: colors.red,
      },
      width: {
        1: "1px",
      },
      borderWidth: {
        1: "1px",
      },
    },
  },
  plugins: [],
};
