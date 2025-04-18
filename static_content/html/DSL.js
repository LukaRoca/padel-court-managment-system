function isElement(obj) {
    return (
        typeof HTMLElement === "object"
            ? obj instanceof HTMLElement
            : obj && typeof obj === "object" && true && obj.nodeType === 1 && typeof obj.nodeName === "string"
    );
}

function appendChild(element, child) {
    if (typeof child === "string") {
        element.appendChild(document.createTextNode(child));
    } else if (isElement(child)) {
        element.appendChild(child);
    } else {
        console.warn("Attempted to append invalid child:", child);
    }
}

function setAttributes(element, attributes) {
    for (const attribute in attributes) {
        if (!Object.prototype.hasOwnProperty.call(attributes, attribute) || attribute == null) {
            continue;
        }

        const value = attributes[attribute];
        if (value == null) continue;

        switch (attribute) {
            case "onClick":
            case "onSubmit":
            case "onInvalid":
            case "onChange":
            case "onInput":
                if (typeof value === 'function') {
                    element.addEventListener(attribute.substring(2).toLowerCase(), value);
                } else {
                    console.warn(`Invalid event handler for ${attribute}: Not a function.`);
                }
                break;

            case "style":
                if (typeof value === 'string') {
                    element.style.cssText = value;
                } else if (typeof value === 'object') {
                    for (const styleProperty in value) {
                        if (Object.prototype.hasOwnProperty.call(value, styleProperty)) {
                            element.style[styleProperty] = value[styleProperty];
                        }
                    }
                } else {
                    console.warn(`Invalid type for style attribute: ${typeof value}`, value);
                }
                break;

            case "ref":
                if (typeof value?.resolve === 'function') {
                    value.resolve(element);
                } else {
                    console.warn("Invalid ref object provided:", value);
                }
                break;

            default:
                if (attribute in element) {
                    try {
                        element[attribute] = value;
                    } catch (e) {
                        element.setAttribute(attribute, value);
                    }
                } else {
                    element.setAttribute(attribute, value);
                }
        }
    }
}

export function createElement(tag, attributes, ...children) {
    if (typeof tag !== 'string') {
        throw new Error("Invalid tag for createElement: Must be a string.");
    }
    const element = document.createElement(tag);

    if (attributes != null && typeof attributes === "object" && !isElement(attributes)) {
        setAttributes(element, attributes);
    } else if (attributes != null) {
        console.error("Invalid attributes argument for createElement. Expected an object or null/undefined, received:", attributes);
        throw new Error("Invalid attributes for createElement. Must be an object or null/undefined.");
    }

    for (const child of children) {
        if (child != null) {
            if (isElement(child) || typeof child === "string" || typeof child === "number") {
                appendChild(element, typeof child === "number" ? String(child) : child);
            } else {
                console.error("Invalid child:", child, "for element:", element);
                throw new Error(`Invalid child type (${typeof child}) for element: ${element.tagName}`);
            }
        }
    }

    return element;
}