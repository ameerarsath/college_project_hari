const { TestEnvironment } = require('jest-environment-jsdom');

class CustomTestEnvironment extends TestEnvironment {
  constructor(config, context) {
    super(config, context);
    
    // Mock canvas to prevent canvas.node issues
    this.global.HTMLCanvasElement = class HTMLCanvasElement {
      constructor() {
        this.width = 0;
        this.height = 0;
      }
      
      getContext() {
        return {
          fillRect: () => {},
          clearRect: () => {},
          getImageData: () => ({ data: new Array(4) }),
          putImageData: () => {},
          createImageData: () => [],
          setTransform: () => {},
          drawImage: () => {},
          save: () => {},
          fillText: () => {},
          restore: () => {},
          beginPath: () => {},
          moveTo: () => {},
          lineTo: () => {},
          closePath: () => {},
          stroke: () => {},
          translate: () => {},
          scale: () => {},
          rotate: () => {},
          arc: () => {},
          fill: () => {},
          measureText: () => ({ width: 0 }),
          transform: () => {},
          rect: () => {},
          clip: () => {},
        };
      }
      
      toDataURL() {
        return 'data:image/png;base64,mock';
      }
    };
  }
}

module.exports = CustomTestEnvironment;