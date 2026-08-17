export function normalizeMoneyInput(value: unknown): string {
  if (value === null || value === undefined) {
    return '';
  }

  const text = String(value).replace(/,/g, '').replace(/[^\d.]/g, '');
  const [integerPart = '', ...decimalParts] = text.split('.');
  const decimals = decimalParts.join('').slice(0, 2);
  const hasDecimal = text.includes('.');

  if (!integerPart && !hasDecimal) {
    return '';
  }

  const integer = stripLeadingZeroes(integerPart);
  return hasDecimal ? `${integer || '0'}.${decimals}` : integer;
}

export function formatMoneyText(value: unknown): string {
  const normalized = normalizeMoneyInput(value);
  if (!normalized) {
    return '';
  }

  const hasDecimal = normalized.includes('.');
  const [integerPart, decimalPart = ''] = normalized.split('.');
  const formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

  return hasDecimal ? `${formattedInteger}.${decimalPart}` : formattedInteger;
}

export function parseMoney(value: unknown): number {
  const normalized = normalizeMoneyInput(value);
  return normalized ? Number(normalized) : 0;
}

function stripLeadingZeroes(value: string): string {
  const stripped = value.replace(/^0+(?=\d)/, '');
  return stripped || value;
}
