import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReunionesPreComponent } from './reuniones-pre.component';

describe('ReunionesPreComponent', () => {
  let component: ReunionesPreComponent;
  let fixture: ComponentFixture<ReunionesPreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReunionesPreComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReunionesPreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
