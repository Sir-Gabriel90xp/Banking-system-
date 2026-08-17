import { AfterViewInit, Directive, ElementRef, HostListener, OnDestroy, Optional, Self } from '@angular/core';
import { NgControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { formatMoneyText, normalizeMoneyInput } from '../utils/money-input.util';

@Directive({
  selector: '[appMoneyInput]',
  standalone: true
})
export class MoneyInputDirective implements AfterViewInit, OnDestroy {
  private valueChanges?: Subscription;
  private writingControl = false;

  constructor(
    private elementRef: ElementRef<HTMLInputElement>,
    @Optional() @Self() private ngControl: NgControl
  ) {}

  ngAfterViewInit(): void {
    queueMicrotask(() => this.render(this.ngControl?.control?.value ?? this.input.value));

    this.valueChanges = this.ngControl?.control?.valueChanges.subscribe((value) => {
      if (!this.writingControl) {
        this.render(value);
      }
    });
  }

  ngOnDestroy(): void {
    this.valueChanges?.unsubscribe();
  }

  @HostListener('input')
  onInput(): void {
    const normalized = normalizeMoneyInput(this.input.value);
    this.writingControl = true;
    this.ngControl?.control?.setValue(normalized, { emitEvent: false });
    this.writingControl = false;
    this.render(normalized);
  }

  @HostListener('blur')
  onBlur(): void {
    this.render(this.ngControl?.control?.value ?? this.input.value);
  }

  private render(value: unknown): void {
    this.input.value = formatMoneyText(value);
    this.input.setSelectionRange(this.input.value.length, this.input.value.length);
  }

  private get input(): HTMLInputElement {
    return this.elementRef.nativeElement;
  }
}
