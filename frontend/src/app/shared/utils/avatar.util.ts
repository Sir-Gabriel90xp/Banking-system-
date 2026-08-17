// src/app/shared/utils/avatar.util.ts

/**
 * UI Avatars (ui-avatars.com) — genera una imagen con las iniciales de un
 * nombre. Sin API key, sin límite de requests, resultado cacheado por
 * ellos mismos. Colores tomados de la paleta "ledger" del proyecto (ink
 * navy de fondo, paper de texto) para que no desentone con el resto de la UI.
 */
export function avatarUrl(name: string, size = 40): string {
  const clean = name.trim() || '?';
  const encoded = encodeURIComponent(clean);
  return `https://ui-avatars.com/api/?name=${encoded}&size=${size}&background=0B1220&color=F6F4EF&rounded=true&bold=true&length=2`;
}