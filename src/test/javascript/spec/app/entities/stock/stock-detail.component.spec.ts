/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { NutritioBackTestModule } from '../../../test.module';
import { StockDetailComponent } from '../../../../../../main/webapp/app/entities/stock/stock-detail.component';
import { StockService } from '../../../../../../main/webapp/app/entities/stock/stock.service';
import { Stock } from '../../../../../../main/webapp/app/entities/stock/stock.model';

describe('Component Tests', () => {

    describe('Stock Management Detail Component', () => {
        let comp: StockDetailComponent;
        let fixture: ComponentFixture<StockDetailComponent>;
        let service: StockService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [StockDetailComponent],
                providers: [
                    StockService
                ]
            })
            .overrideTemplate(StockDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StockDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Stock(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.stock).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
