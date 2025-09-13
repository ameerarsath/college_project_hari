module.exports = {
  testEnvironment: 'jsdom',
  setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
  moduleNameMapping: {
    '^canvas$': '<rootDir>/src/__mocks__/canvas.js'
  }
};