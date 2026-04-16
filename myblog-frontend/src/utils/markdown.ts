function renderInline(text: string) {
  return text
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>');
}

export function renderMarkdown(content: string) {
  const blocks = content
    .trim()
    .split(/\n{2,}/)
    .map((block) => block.trim())
    .filter(Boolean);

  return blocks
    .map((block) => {
      const lines = block.split('\n').map((line) => line.trim());

      if (block.startsWith('## ')) {
        return `<h2>${renderInline(block.replace(/^## /, ''))}</h2>`;
      }

      if (block.startsWith('# ')) {
        return `<h1>${renderInline(block.replace(/^# /, ''))}</h1>`;
      }

      if (lines.every((line) => line.startsWith('- '))) {
        return `<ul>${lines
          .map((line) => `<li>${renderInline(line.replace(/^- /, ''))}</li>`)
          .join('')}</ul>`;
      }

      return `<p>${lines.map((line) => renderInline(line)).join('<br />')}</p>`;
    })
    .join('');
}
