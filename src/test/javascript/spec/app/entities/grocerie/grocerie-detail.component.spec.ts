/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { NutritioBackTestModule } from '../../../test.module';
import { GrocerieDetailComponent } from '../../../../../../main/webapp/app/entities/grocerie/grocerie-detail.component';
import { GrocerieService } from '../../../../../../main/webapp/app/entities/grocerie/grocerie.service';
import { Grocerie } from '../../../../../../main/webapp/app/entities/grocerie/grocerie.model';

describe('Component Tests', () => {

    describe('Grocerie Management Detail Component', () => {
        let comp: GrocerieDetailComponent;
        let fixture: ComponentFixture<GrocerieDetailComponent>;
        let service: GrocerieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [GrocerieDetailComponent],
                providers: [
                    GrocerieService
                ]
            })
            .overrideTemplate(GrocerieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrocerieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrocerieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Grocerie(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.grocerie).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
